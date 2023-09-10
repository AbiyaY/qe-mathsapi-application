package com.qeproject.mathsapi.services;

import com.qeproject.mathsapi.TestUtils;
import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

// Note on @DirtiesContext: This seems to cause the reinitialisation of the context
// of the test. When removed, we may observe the following behaviour:
// - testConcurrency() may run after testOperators()
// - the above may leave dirty data in the MathsService class, which is effectively
//   caching the last used numbers and operator
// - Hence testConcurrency() is likely to work with number values that were left
//   behind by a previous test case. DirtiesContext seems to resolve this.

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MathsServiceTest
{
    @Autowired
    MathsService service;

    Double result;

    /**
     * The implementation of MathService relies on wait() and notifyAll() invocations.
     * This tests aims at ensuring that the order of invocations won't cause a deadlock
     */
    @Test
    void testConcurrency()
    {
        Thread t1 = new Thread(() -> { result = service.storeOperator(OperatorEnum.MULTIPLY); });
        Thread t2 = new Thread(() -> service.storeIntegers(new NumbersObject(3, 5)) );

        t1.start();
        TestUtils.sleep(100); // give time to ensure MathsService.storeOperator goes into wait
        t2.start();

        while (result == null)
        {
            TestUtils.sleep(10);
        }

        Assertions.assertThat(result).isEqualTo(15);
    }

    @Test
    void testOperators()
    {
        service.storeIntegers(new NumbersObject(3, 6));

        assertThat(service.storeOperator(OperatorEnum.ADD)).isEqualTo(9.0);
        assertThat(service.storeOperator(OperatorEnum.SUBTRACT)).isEqualTo(-3.0);
        assertThat(service.storeOperator(OperatorEnum.DIVIDE)).isEqualTo(0.5);
        assertThat(service.storeOperator(OperatorEnum.MULTIPLY)).isEqualTo(18.0);

        service.storeIntegers(new NumbersObject(3, -6));

        assertThat(service.storeOperator(OperatorEnum.ADD)).isEqualTo(-3.0);
        assertThat(service.storeOperator(OperatorEnum.SUBTRACT)).isEqualTo(9.0);
        assertThat(service.storeOperator(OperatorEnum.DIVIDE)).isEqualTo(-0.5);
        assertThat(service.storeOperator(OperatorEnum.MULTIPLY)).isEqualTo(-18.0);

        service.storeIntegers(new NumbersObject(1.5, 2));

        assertThat(service.storeOperator(OperatorEnum.ADD)).isEqualTo(3.5);
        assertThat(service.storeOperator(OperatorEnum.SUBTRACT)).isEqualTo(-0.5);
        assertThat(service.storeOperator(OperatorEnum.DIVIDE)).isEqualTo(0.75);
        assertThat(service.storeOperator(OperatorEnum.MULTIPLY)).isEqualTo(3.0);

        service.storeIntegers(new NumbersObject(-1.5, 2));
        assertThat(service.storeOperator(OperatorEnum.MULTIPLY)).isEqualTo(-3.0);
    }
}