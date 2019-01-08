package properties.messages.wrapper;

import string.pattern.common.core.PatternReplacer;
import string.pattern.common.core.PatternSearcher;

public class PatternRuleWrapper {

    public PatternSearcher searcher;
    public PatternReplacer replacer;

    public PatternRuleWrapper(PatternSearcher searcher, PatternReplacer replacer) {
        this.searcher = searcher;
        this.replacer = replacer;
    }
}
