import java.io.*;
import java.lang.reflect.Method;

/**
 * 极客大学week01 第二题作业
 * 自定义一个 Classloader，加载 Hello.xlass 文件，执行 hello 方法
 * 此文件内容是 Hello.class 中所有字节(x=255-x)被处理后的文件
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Object hello = new HelloClassLoader().findClass("Hello").newInstance();
            Method method = hello.getClass().getDeclaredMethod("hello", null);
            method.invoke(hello, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File file = new File("/Users/moby/Desktop/Hello/Hello.xlass");
            byte[] bytes = getBytes(file);
            return this.defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public static byte[] getBytes(File file) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            while (true) {
                int read = fis.read();
                if (read == -1) break;
                int realRead = 255 - read;
                baos.write(realRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }
}
