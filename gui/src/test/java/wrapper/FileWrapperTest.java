package wrapper;

import com.konai.common.core.Expression;
import org.junit.Assert;
import org.junit.Test;
import properties.messages.wrapper.FileWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWrapperTest {

    @Test
    public void createInstance() throws IOException {
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        FileWrapper fileWrapper = new FileWrapper(htmlFile);
    }

    @Test
    public void getExpressions() throws IOException {
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        FileWrapper fileWrapper = new FileWrapper(htmlFile);
        List<Expression> lines = fileWrapper.getExpressions();
        Assert.assertEquals(15, lines.size());
        for (Expression expression : lines) {
            System.out.println(expression.getValue());
        }
    }

}