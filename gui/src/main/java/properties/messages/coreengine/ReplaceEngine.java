package properties.messages.coreengine;

import string.pattern.common.core.PatternSearcher;
import string.pattern.common.core.Expression;
import string.pattern.common.vo.KeyValue;
import string.pattern.replace.core.KeyValueReplacer;
import string.pattern.common.core.PatternReplacer;

import java.util.List;

public class ReplaceEngine {

    private List<KeyValue> messageProperties;
    private List<Expression> expressions;
    private KeyValueReplacer replacer;

    public ReplaceEngine() {
        replacer = new KeyValueReplacer();
    }

    public ReplaceEngine set(List<KeyValue> messageProperties, List<Expression> expressions) {
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
