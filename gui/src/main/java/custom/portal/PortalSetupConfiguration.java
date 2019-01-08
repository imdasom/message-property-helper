package custom.portal;

import custom.portal.pattern.BetweenHtmlTagPatternSearcher;
import custom.portal.pattern.ThymeleafTextValuePatternSearcher;
import custom.portal.pattern.ThymeleafTextValuePatterner;
import properties.messages.wrapper.PatternRuleWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PortalSetupConfiguration {

    // TODO 모든 리소스 가져오도록 수정해야 함
    public ResourceBundleWrapper getResourceBundleWrapper(String projectPath) throws IOException {
        String resourceLocation = "\\src\\test\\resources\\";
        String fullLocation = projectPath + resourceLocation;
        Locale locale = new Locale("ko", "KR");
        ResourceBundleWrapper resourceBundleWrapper = new ResourceBundleWrapper();
        resourceBundleWrapper.add(fullLocation, "messages", locale);
        resourceBundleWrapper.add(fullLocation, "product", locale);
        return resourceBundleWrapper;
    }

    public List<PortalFileWrapper> getFileWrappers(List<File> files) throws IOException {
        List<PortalFileWrapper> fileWrappers = new ArrayList<>();
        for (File file : files) {
            List<PatternRuleWrapper> patternRules = getPatternRules();
            PortalFileWrapper portalFileWrapper = new PortalFileWrapper(file);
            portalFileWrapper.setCollectPattern(ThymeleafTextValuePatternSearcher.getInstance());
            portalFileWrapper.setPatternRuleWrappers(patternRules);
            fileWrappers.add(portalFileWrapper);
        }
        return fileWrappers;
    }

    public List<PatternRuleWrapper> getPatternRules() {
        ThymeleafTextValuePatternSearcher thymeleafTextPatternReplacer = ThymeleafTextValuePatternSearcher.getInstance();
        ThymeleafTextValuePatterner thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();
        BetweenHtmlTagPatternSearcher plainValuePatterner = new BetweenHtmlTagPatternSearcher();
        List<PatternRuleWrapper> patternRules = new ArrayList<>();
        patternRules.add(new PatternRuleWrapper(thymeleafTextValuePatterner, thymeleafTextPatternReplacer));
        patternRules.add(new PatternRuleWrapper(plainValuePatterner, plainValuePatterner));
        return patternRules;
    }
}
