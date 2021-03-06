package properties.messages.wrapper;

import string.pattern.common.util.FileUtils;
import string.pattern.common.vo.Key;
import string.pattern.common.vo.KeyValue;
import string.pattern.common.vo.Value;
import string.pattern.search.util.KeyValueTokenizer;
import string.pattern.search.vo.Message;

import java.io.IOException;
import java.util.*;

public class ResourceBundleWrapper {

    private Map<Key, Message> resourceMap;

    public ResourceBundleWrapper() {
        resourceMap = new HashMap<>();
    }

    public void add(String location, String bundleName, Locale locale) throws IOException {
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, locale);
        KeyValueTokenizer tokenizer = new KeyValueTokenizer();
        Map<String, String> messagePropertyMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messagePropertyMap);
        resourceMap.putAll(resourceTokenList);
    }

    public Map<Key, Message> getResourceMap() {
        return resourceMap;
    }

    public List<KeyValue> getResourceMapToList() {
        List<KeyValue> messageProperties = new ArrayList<>();
        for(Map.Entry e : resourceMap.entrySet()) {
            Key key = new Key(String.valueOf(e.getKey()));
            Value value = new Value(String.valueOf(e.getValue()));
            messageProperties.add(new KeyValue(key, value));
        }
        return messageProperties;
    }
}
