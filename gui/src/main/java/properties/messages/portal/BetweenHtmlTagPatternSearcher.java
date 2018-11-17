package properties.messages.portal;

import com.konai.common.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.core.PatternReplacer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetweenHtmlTagPatternSearcher implements PatternSearcher, PatternReplacer {

    private final String REGULA_REXPRESSION = "(<){1}(.+?){1}(>){1}(.+?){1}(</.+?>){1}";
    private final Pattern thymeleafTextExpression = Pattern.compile(REGULA_REXPRESSION);

    @Override
    public List<Expression> get(Expression source) {
        List<Expression> expresisons = new ArrayList<>();
        Matcher matcher = thymeleafTextExpression.matcher(source.getValue());
        while(matcher.find()) {
            String message = matcher.group(4);
            expresisons.add(new Expression(message));
        }
        return expresisons;
    }

    @Override
    public Expression replace(Expression destination, Expression targetExpression, Expression replacementExpression) {
        String desintation = destination.getValue();
        String target = targetExpression.getValue();
        String replacement = "th:text=\"#{" + replacementExpression.getValue() + "}\"";
        Matcher matcher = thymeleafTextExpression.matcher(desintation);
        Expression afterReplace = destination;
        while(matcher.find()) {
            if(matcher.group(4).contains(target)) {
                String addAttributes = matcher.group(2) + " " + replacement;
                StringBuilder sb = new StringBuilder(desintation).replace(matcher.start(2), matcher.end(2), addAttributes);
                afterReplace = new Expression(sb.toString());
            }
        }
        return afterReplace;
    }
}
