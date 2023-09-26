import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;



// class to override the writeStreamHeader method of ObjectOutputStream class,
// so that multiple objects can be serialized and desrialized from the same
// file.
public class MyObjectOutputStream extends ObjectOutputStream{
    MyObjectOutputStream() throws IOException {
        super();
    }

    MyObjectOutputStream(OutputStream o) throws IOException {
        super(o);
    }

    public void writeStreamHeader() throws IOException {
        return;
    }
}
