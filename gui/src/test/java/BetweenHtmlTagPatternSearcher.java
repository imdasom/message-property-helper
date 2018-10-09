import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.replace.core.PatternReplacer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThymeleafTextPatternSearcher implements PatternSearcher<Expression, Expression>, PatternReplacer<Expression, Expression> {

    private final String REGULA_REXPRESSION = "(th:text=\"){1}(.*?){1}(\"){1}";
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
