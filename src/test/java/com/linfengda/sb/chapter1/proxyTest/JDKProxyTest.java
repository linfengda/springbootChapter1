package com.linfengda.sb.chapter1.proxyTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-12-24 15:28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JDKProxyTest {

    /**
     * JDK动态代理使我们可以在运行期动态的创建代理类。动态代理是在运行期间通过接口生成代理类的，与静态代理相比更加灵活，但是也有一定的限制，
     * 第一是代理对象必须实现一个接口，否则会报异常，因为其原理就是根据接口来生成代理对象的。
     * 第二是有性能问题，因为是通过反射来实现调用的，所以比正常的直接调用来得慢，并且通过生成类文件也会多消耗部分方法区空间，可能引起Full GC
     *
     * 从Proxy.newProxyInstance()方法开始：
     * 首先加载被代理类的接口的class，然后通过执行方法java.lang.reflect.Proxy.ProxyClassFactory#apply创建对应proxy class，
     * 其内部会调用ProxyGenerator.generateProxyClass()方法生成二进制class数据，
     * 然后执行java.lang.reflect.Proxy#defineClass0返回对应class实例，至此生成proxy class流程就完成了。
     * @throws Exception
     */
    @org.junit.Test
    public void testJDKProxy() throws Exception {
        ProxyHandler proxyHandler = new ProxyHandler();
        Subject subject = (Subject) proxyHandler.bind(new RealSubject());
        printProxyClass("C:\\Users\\SI-GZ-1134\\Desktop\\TestProxy.class");
        subject.doSomething();
    }

    /**
     * 将动态生成的代理类打印出来（http://www.javadecompilers.com/result  可利用在线反编译查看代码）
     * @param path
     */
    private void printProxyClass(String path) {
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", RealSubject.class.getInterfaces());
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
