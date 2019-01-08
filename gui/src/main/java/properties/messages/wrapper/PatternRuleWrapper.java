package properties.messages.wrapper;

import com.konai.common.core.PatternReplacer;
import com.konai.common.core.PatternSearcher;

public class PatternRuleWrapper {

    public PatternSearcher searcher;
    public PatternReplacer replacer;

    public PatternRuleWrapper(PatternSearcher searcher, PatternReplacer replacer) {
        this.searcher = searcher;
        this.replacer = replacer;
    }
}
