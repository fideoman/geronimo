/* ====================================================================
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce this list of
 *    conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. The name "OpenEJB" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of The OpenEJB Group.  For written permission,
 *    please contact openejb-group@openejb.sf.net.
 *
 * 4. Products derived from this Software may not be called "OpenEJB"
 *    nor may "OpenEJB" appear in their names without prior written
 *    permission of The OpenEJB Group. OpenEJB is a registered
 *    trademark of The OpenEJB Group.
 *
 * 5. Due credit should be given to the OpenEJB Project
 *    (http://openejb.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY THE OPENEJB GROUP AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * THE OPENEJB GROUP OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the OpenEJB Project.  For more information
 * please see <http://openejb.org/>.
 *
 * ====================================================================
 */
package org.apache.geronimo.j2ee.deployment;

import java.io.File;
import java.net.URI;
import javax.management.ObjectName;

import junit.framework.TestCase;
import org.apache.geronimo.deployment.DeploymentException;
import org.apache.geronimo.kernel.jmx.JMXUtil;
import org.apache.xmlbeans.XmlObject;

/**
 * @version $Revision: 1.1 $ $Date: 2004/05/19 20:53:59 $
 */
public class EARConfigBuilderTest extends TestCase {
    private static final ObjectName j2eeServer = JMXUtil.getObjectName("someDomain:j2eeType=J2EEServer,name=J2EEServerName");

    public void testBuildConfiguration() throws Exception {
        MockEJBConfigBuilder ejbConfigBuilder = new MockEJBConfigBuilder();
        MockWARConfigBuilder webConfigBuilder = new MockWARConfigBuilder();
        MockConnectorConfigBuilder connectorConfigBuilder = new MockConnectorConfigBuilder();
        EARConfigBuilder configBuilder = new EARConfigBuilder(null, null, j2eeServer, ejbConfigBuilder, webConfigBuilder, connectorConfigBuilder);
        File earFile = new File("target/test-ear.ear");

        File carFile = File.createTempFile("EARTest", ".car");
        try {
            ejbConfigBuilder.ejbModule = new EJBModule("test-ejb-jar.jar", URI.create("test-ejb-jar.jar"));
            webConfigBuilder.contextRoot = "test";
            webConfigBuilder.webModule = new WebModule("test-war.war", URI.create("test-war.war"), "test");
            connectorConfigBuilder.connectorModule = new ConnectorModule("test-rar.rar", URI.create("test-rar.rar"));

            XmlObject plan = configBuilder.getDeploymentPlan(earFile.toURL());
            configBuilder.buildConfiguration(carFile, null, earFile, plan);
        } finally {
            carFile.delete();
        }
    }

    public void testNoEJBDeployer() throws Exception {
        MockWARConfigBuilder warConfigBuilder = new MockWARConfigBuilder();
        MockConnectorConfigBuilder connectorConfigBuilder = new MockConnectorConfigBuilder();
        EARConfigBuilder configBuilder = new EARConfigBuilder(null, null, j2eeServer, null, warConfigBuilder, connectorConfigBuilder);
        File earFile = new File("target/test-ear.ear");

        File carFile = File.createTempFile("EARTest", ".car");
        try {
            XmlObject plan = configBuilder.getDeploymentPlan(earFile.toURL());
            configBuilder.buildConfiguration(carFile, null, earFile, plan);
            fail("Should have thrown a DeploymentException");
        } catch (DeploymentException e) {
            // expected
        } finally {
            carFile.delete();
        }
    }

    public void testNoWARDeployer() throws Exception {
        MockEJBConfigBuilder ejbConfigBuilder = new MockEJBConfigBuilder();
        MockConnectorConfigBuilder connectorConfigBuilder = new MockConnectorConfigBuilder();
        EARConfigBuilder configBuilder = new EARConfigBuilder(null, null, j2eeServer, ejbConfigBuilder, null, connectorConfigBuilder);
        File earFile = new File("target/test-ear.ear");

        File carFile = File.createTempFile("EARTest", ".car");
        try {
            XmlObject plan = configBuilder.getDeploymentPlan(earFile.toURL());
            configBuilder.buildConfiguration(carFile, null, earFile, plan);
            fail("Should have thrown a DeploymentException");
        } catch (DeploymentException e) {
            // expected
        } finally {
            carFile.delete();
        }
    }

    public void testNoConnectorDeployer() throws Exception {
        MockWARConfigBuilder warConfigBuilder = new MockWARConfigBuilder();
        MockEJBConfigBuilder ejbConfigBuilder = new MockEJBConfigBuilder();
        EARConfigBuilder configBuilder = new EARConfigBuilder(null, null, j2eeServer, ejbConfigBuilder, warConfigBuilder, null);
        File earFile = new File("target/test-ear.ear");

        File carFile = File.createTempFile("EARTest", ".car");
        try {
            XmlObject plan = configBuilder.getDeploymentPlan(earFile.toURL());
            configBuilder.buildConfiguration(carFile, null, earFile, plan);
            fail("Should have thrown a DeploymentException");
        } catch (DeploymentException e) {
            // expected
        } finally {
            carFile.delete();
        }
    }

}
