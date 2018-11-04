package portal;

import com.konai.common.core.Expression;
import com.konai.common.util.StringUtils;
import com.konai.common.vo.Key;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.Message;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortalKeyNameRule implements KeyNameRule {

    private String prefix;
    private String delimeter;
    private int lastIndex;

    public PortalKeyNameRule(String prefix, String delimeter, Map<Key, Message> messagePropertyMap) {
        this.prefix = prefix;
        this.delimeter = delimeter;
        this.lastIndex = getLastIndex(messagePropertyMap);
    }

    @Override
    public String getKey(Expression expression) {
        String suffix = StringUtils.getZeroPaddingNumber(++lastIndex, 4);
        return prefix + delimeter + suffix;
    }

    private int getLastIndex(Map<Key, Message> messagePropertyMap) {
        int maxCounter = 0;
        if(messagePropertyMap != null) {
            for(Map.Entry<Key, Message> entry : messagePropertyMap.entrySet()) {
                String key = entry.getKey().getValue();
                Pattern findNumber = Pattern.compile("("+prefix+"){1}"+"("+delimeter+"){1}(.+){1}");
                Matcher matcher = findNumber.matcher(key);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(3));
                    if(maxCounter < number) {
                        maxCounter = number;
                    }
                }
            }
        }
        return maxCounter;
    }
}
