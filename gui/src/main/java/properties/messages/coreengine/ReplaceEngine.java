package properties.messages.coreengine;

import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.vo.MessageProperty;
import com.konai.replace.core.MessagePropertyReplacer;
import com.konai.replace.core.PatternReplacer;

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

    public ReplaceEngine replace(PatternSearcher patternSearcher, PatternReplacer patternReplacer) {
        expressions = replacer.replace(
                messageProperties, expressions, patternSearcher, patternReplacer
        );
        return this;
    }

    public List<Expression> get() {
        return expressions;
    }
}
