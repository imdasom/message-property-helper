package properties.messages;

import com.konai.common.core.Expression;
import com.konai.common.vo.MessageProperty;
import com.konai.replace.core.MessagePropertyReplacer;
import properties.messages.portal.ThymeleafTextPatternSearcher;
import properties.messages.portal.ThymeleafTextValuePatterner;

import java.util.List;

public class ReplaceEngine {

    private List<MessageProperty> messageProperties;
    private List<Expression> expressions;
    private MessagePropertyReplacer replacer;

    public ReplaceEngine() {
        replacer = new MessagePropertyReplacer();
    }

    public ReplaceEngine set(List<MessageProperty> messageProperties, List<Expression> expressions) {
        this.messageProperties = messageProperties;
        this.expressions = expressions;
        return this;
    }

    public ReplaceEngine replace(ThymeleafTextValuePatterner searchPattern, ThymeleafTextPatternSearcher replacePattern) {
        expressions = replacer.replace(
                messageProperties, expressions, searchPattern, replacePattern
        );
        return this;
    }

    public List<Expression> get() {
        return expressions;
    }
}
