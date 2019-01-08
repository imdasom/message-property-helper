package custom.portal.pattern;

import string.pattern.common.core.PatternSearcher;
import string.pattern.common.core.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThymeleafTextValuePatterner implements PatternSearcher {

    private final String REGULA_REXPRESSION = "(th:text=\"'){1}(.*?){1}('\"){1}";
    private final Pattern thymeleafTextExpression = Pattern.compile(REGULA_REXPRESSION);

    @Override
    public List<Expression> get(Expression source) {
        List<Expression> expresisons = new ArrayList<>();
        Matcher matcher = thymeleafTextExpression.matcher(source.getValue());
        while(matcher.find()) {
            String message = matcher.group(2);
            expresisons.add(new Expression(message));
        }
        return expresisons;
    }
}
