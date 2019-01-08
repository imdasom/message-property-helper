package custom.portal.pattern;

import string.pattern.common.core.PatternSearcher;
import string.pattern.common.core.Expression;
import string.pattern.common.core.PatternReplacer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThymeleafTextValuePatternSearcher implements PatternSearcher, PatternReplacer {

    private static ThymeleafTextValuePatternSearcher instance;
    private ThymeleafTextValuePatternSearcher() {}
    private final String REGULA_REXPRESSION = "(th:text=\"){1}(.*?){1}(\"){1}";
    private final Pattern thymeleafTextExpression = Pattern.compile(REGULA_REXPRESSION);

    public static ThymeleafTextValuePatternSearcher getInstance() {
        if(instance == null) {
            instance = new ThymeleafTextValuePatternSearcher();
        }
        return instance;
    }

    @Override
    public List<Expression> get(Expression source) {
        List<Expression> expresisons = new ArrayList<>();
        Matcher matcher = thymeleafTextExpression.matcher(source.getValue());
        while(matcher.find()) {
            String message = matcher.group(2);
            if(message.startsWith("'")) {
                message = message.replaceAll("'", "");
                expresisons.add(new Expression(message));
            }
        }
        return expresisons;
    }

    @Override
    public Expression replace(Expression destination, Expression targetExpression, Expression replacementExpression) {
        String desintation = destination.getValue();
        String target = targetExpression.getValue();
        String replacement = "#{" + replacementExpression.getValue() + "}";
        Matcher matcher = thymeleafTextExpression.matcher(desintation);
        Expression afterReplace = destination;
        while(matcher.find()) {
            if(matcher.group(2).contains(target)) {
                StringBuilder sb = new StringBuilder(desintation).replace(matcher.start(2), matcher.end(2), replacement);
                afterReplace = new Expression(sb.toString());
            }
        }
        return afterReplace;
    }
}
