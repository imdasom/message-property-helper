package properties.messages.wrapper;

import string.pattern.common.core.Expression;
import string.pattern.common.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class FileWrapper {

    protected List<Expression> expressions;

    public FileWrapper(File file) throws IOException {
        InputStream inputStream = FileUtils.getInputStream(file);
        List<String> lines = FileUtils.readLines(inputStream);
        expressions = lines.stream().map(Expression::new).collect(Collectors.toList());
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

}
