import java.io.*;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 极客大学week01 第二题作业
 * 自定义一个 Classloader，加载 Hello.xlass 文件，执行 hello 方法
 * 此文件内容是 Hello.class 中所有字节(x=255-x)被处理后的文件
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassLoader().findClass("Hello");
            Method method = clazz.getDeclaredMethod("hello");
            method.setAccessible(true);
            method.invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File file = new File("/Users/moby/Desktop/Hello/Hello.xlass");
            Optional<byte[]> optional = getBytes(file);
            if (optional.isPresent()) {
                byte[] bytes = optional.get();
                return defineClass(name, bytes, 0, bytes.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public static Optional<byte[]> getBytes(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            while (true) {
                int read = fis.read();
                if (read == -1) {
                    break;
                }
                baos.write(255 - read);
            }
            return Optional.ofNullable(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
