package com.qeproject.mathsapi.services;

import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
class MathsServiceTest
{
    MathsService service = new MathsService();
    Double result;

    @Test
    void testSomething()
    {
        Thread t1 = new Thread(() -> this.doT1Stuff());

        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("t2 entered");
                service.storeIntegers(new NumbersObject(3, 5));
//                System.out.println("t2 notify");
//                synchronized (MathsServiceTest.class)
//                {
//                    MathsServiceTest.class.notify();
//                }
                System.out.println("t2 ending");
            }
        });

//        Thread resultThread = new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
////                System.out.println("resultThread entered");
////
////                while (result == null)
////                {
////                    try
////                    {
////                        System.out.println("resultThread wait");
////                        synchronized (MathsServiceTest.class)
////                        {
////                            MathsServiceTest.class.wait();
////                        }
////                        System.out.println("resultThread awake");
////                    }
////                    catch (InterruptedException iex)
////                    {
////                        // CNR
////                    }
////                }
//
////                System.out.println("resultThread assert");
////                Assertions.assertThat(result).isEqualTo(15);
//            }
//        });


        t1.start();
        t2.start();
//        resultThread.start();

        while (result == null)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException iex)
            {
                // CNR
            }
        }

        Assertions.assertThat(result).isEqualTo(115);
    }

    private void doT1Stuff()
    {
        System.out.println("t1 entered");
        result = service.storeOperator(OperatorEnum.MULTIPLY);
//        System.out.println("t1 #notify");

//        synchronized (MathsServiceTest.class)
//        {
//            MathsServiceTest.class.notify();
//        }

        System.out.println("t1 ending");
    }
}