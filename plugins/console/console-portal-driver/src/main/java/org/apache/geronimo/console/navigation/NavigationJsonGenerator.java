package org.apache.geronimo.console.navigation;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.geronimo.pluto.impl.PageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class is used to generate JSON tree for a list of PageConfig.
 * 
 * Sample:  
 * 
 * pageName of pageConfig1: 1-1/server/server log
 * pageName of pageConfig2: 2-1/applications/web applications
 * pageName of pageConfig3: 1-2/server/server info
 * 
 * will result in following navigation tree:
 * 
 * --server
 *      --server log
 *      --server info
 * --applications
 *      --web applications
 */
public class NavigationJsonGenerator {
     
    private static final Logger log = LoggerFactory.getLogger(NavigationJsonGenerator.class);
    
    public String generateTreeJSON(List<PageConfig> pageConfigList, String contextPath, String DefaultIcon) {
       
        Map<String, TreeNode> navigationTree = new TreeMap<String, TreeNode>();
        
        for (PageConfig pc : pageConfigList) {
            try {
                new TreeNode(pc).populateTree(navigationTree);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                continue;
            }
        }

        StringBuffer sb = new StringBuffer(10);
        sb.append("[");
        
        
        for (TreeNode node : navigationTree.values()) {

            if (node.isTopNode()) {
                sb.append("\n");
                appendNodeToTreeJSON(sb, node, contextPath, DefaultIcon);
            }

        }
        //remove the extra ','
        sb.deleteCharAt(sb.length()-1);

        sb.append("\n]");
        return sb.toString();
    }
    
    private void appendNodeToTreeJSON(StringBuffer sb, TreeNode node, String contextPath, String DefaultIcon) {

        sb.append("{");

        

        if (!node.isLeafNode()) {
            sb.append("label: \'"+node.getLabel() + "\'");
        } else {
            sb.append("label: \'<img src=\"" + contextPath + node.getIcon() + "\" alt=\"\" border=\"0\">&nbsp;");
            sb.append("<a href=\"" + contextPath + "/portal/" + node.getId() + "/" + node.getPath() + "\">"
                    + node.getLabel() + "</a>\'");
        }

        sb.append(",");
        sb.append("id: \'" + node.getId() + "\'");

        if (node.getChildren() != null) {
            sb.append(",");
            sb.append("children: [");

            for (TreeNode child : node.getChildren().values()) {
                appendNodeToTreeJSON(sb, child, contextPath, DefaultIcon);
            }
            
            //remove the extra ','
            sb.deleteCharAt(sb.length()-1);
            
            sb.append("]\n");
        }

        sb.append("},");

    }
    
    public String generateQuickLauncherJSON(List<PageConfig> pageConfigList, String contextPath, String DefaultIcon) {
        
        Map<String, TreeNode> navigationTree = new TreeMap<String, TreeNode>();
        
        for (PageConfig pc : pageConfigList) {
            try {
                new TreeNode(pc).populateTree(navigationTree);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                continue;
            }
        }

        StringBuffer sb = new StringBuffer(10);
        sb.append("[\n");
        
        for (TreeNode node : navigationTree.values()) {

            if (node.isTopNode()) {
                appendNodeToQuickLauncherJSON(sb, node, contextPath, DefaultIcon);
            }

        }
        //remove the extra ','
        sb.deleteCharAt(sb.length()-1);

        sb.append("\n]");
        return sb.toString();
    }

    private void appendNodeToQuickLauncherJSON(StringBuffer sb, TreeNode node, String contextPath, String DefaultIcon) {

        if (node.isLeafNode()) {
            sb.append("\n{");
            sb.append("label: \'<img src=\"").append(contextPath).append(node.getIcon()).append("\">&nbsp;");
            sb.append("<span>").append(node.getLabel()).append("</span>\'");
            sb.append(",");
            sb.append("name: \'").append(node.getLabel()).append("\'");
            sb.append("},");
        }
        
        if (node.getChildren() != null) {

            for (TreeNode child : node.getChildren().values()) {
                appendNodeToQuickLauncherJSON(sb, child, contextPath, DefaultIcon);
            }
            
            //remove the extra ','
            //sb.deleteCharAt(sb.length()-1);           
        }



    }
    
 public String generateLinks(List<PageConfig> pageConfigList, String contextPath, String DefaultIcon) {
        
        Map<String, TreeNode> navigationTree = new TreeMap<String, TreeNode>();
        
        for (PageConfig pc : pageConfigList) {
            try {
                new TreeNode(pc).populateTree(navigationTree);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                continue;
            }
        }

        StringBuffer sb = new StringBuffer(10);
        sb.append("<ul>\n");
        
        for (TreeNode node : navigationTree.values()) {

            if (node.isTopNode()) {
                appendNodeToLinks(sb, node, contextPath, DefaultIcon);
            }

        }

        sb.append("</ul>");
        return sb.toString();
    }

    private void appendNodeToLinks(StringBuffer sb, TreeNode node, String contextPath, String DefaultIcon) {

        if (node.isLeafNode()) {
            sb.append("<li>");
            sb.append("<a href=\"" + contextPath + "/portal/" + node.getId() + "/" + node.getPath() + "\">"
                    + node.getLabel() + "</a>");
            sb.append("</li>\n");
        }
        
        if (node.getChildren() != null) {

            for (TreeNode child : node.getChildren().values()) {
                appendNodeToLinks(sb, child, contextPath, DefaultIcon);
            }
        
        }



    }

}
