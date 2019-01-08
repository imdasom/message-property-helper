package custom.portal;

import com.konai.common.core.Expression;
import com.konai.common.core.PatternSearcher;
import com.konai.common.vo.KeyValue;
import com.konai.search.vo.ResultClass;
import properties.messages.coreengine.PatternInformationGetter;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.PatternRuleWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PortalFileWrapper extends FileWrapper implements PatternInformationGetter {

    private List<KeyValue> generatedMessages;
    private String keyName;
    private List<PatternRuleWrapper> patternRuleWrappers;
    private PatternSearcher collectPattern;

    public PortalFileWrapper(File file) throws IOException {
        super(file);
        keyName = "PROD_MANA"; //TODO 파일에서 읽어와서 설정하도록 해야 함
    }

    public void setGeneratedMessages(List<KeyValue> generatedMessages) {
        this.generatedMessages = generatedMessages;
    }

    public List<KeyValue> getGeneratedMessages() {
        return generatedMessages;
    }

    @Override
    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public ResultClass getSearchLevel() {
        return ResultClass.TotalSimilar;
    }

    @Override
    public PatternSearcher getCollectPattern() {
        return collectPattern;
    }

    public void setCollectPattern(PatternSearcher collectPattern) {
        this.collectPattern = collectPattern;
    }

    @Override
    public List<PatternRuleWrapper> getPatternRuleWrappers() {
        return patternRuleWrappers;
    }

    public void setPatternRuleWrappers(List<PatternRuleWrapper> patternRuleWrappers) {
        this.patternRuleWrappers = patternRuleWrappers;
    }
}
