package properties.messages.wrapper;

import com.konai.common.core.Expression;
import com.konai.common.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class FileWrapper {

    private List<Expression> expressions;

    public FileWrapper(File file) throws IOException {
        InputStream inputStream = FileUtils.getInputStream(file);
        List<String> lines = FileUtils.readLines(inputStream);
        expressions = lines.stream().map(Expression::new).collect(Collectors.toList());
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}