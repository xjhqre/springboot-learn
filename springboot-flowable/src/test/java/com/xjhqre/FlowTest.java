package com.xjhqre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * FlowTest
 * <p>
 *
 * @author xjhqre
 * @since 8月 23, 2023
 */
@Slf4j
public class FlowTest {

    StandaloneProcessEngineConfiguration configuration;

    @Before
    public void before() {
        // 获取 ProcessEngineConfiguration 对象
        this.configuration = new StandaloneProcessEngineConfiguration();
        // 配置相关的数据库连接信息
        this.configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        this.configuration.setJdbcUsername("root");
        this.configuration.setJdbcPassword("123456");
        this.configuration.setJdbcUrl(
            "jdbc:mysql://localhost:3306/flowable-learn?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&nullCatalogMeansCurrent=true");
        // 如果数据库中的表结构不存在就新建
        this.configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }

    @Test
    public void processEngineTest() {
        // 配置相关的数据库连接信息
        this.configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        this.configuration.setJdbcUsername("root");
        this.configuration.setJdbcPassword("123456");
        this.configuration.setJdbcUrl(
            "jdbc:mysql://localhost:3306/flowable-learn?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&nullCatalogMeansCurrent=true");

        // 如果数据库中的表结构不存在就新建
        this.configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 通过 ProcessEngineConfiguration 构建我们需要的ProcessEngineConfiguration 对象
        ProcessEngine processEngine = this.configuration.buildProcessEngine();

        log.info(String.valueOf(processEngine));
    }

    /**
     * 流程部署
     */
    @Test
    public void testDeploy() {
        // 1、获取 ProcessEngineConfiguration 对象
        ProcessEngine processEngine = this.configuration.buildProcessEngine();

        // 2、获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 3、根据流程图xml文件部署流程定义
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("holiday-request.bpmn20.xml")
            .name("请假流程").deploy();

        log.info("流程定义ID：{}", deploy.getId());
        log.info("流程定义名称：{}", deploy.getName());
    }

    /**
     * 流程查询
     */
    @Test
    public void testDeployQuery() {
        ProcessEngine processEngine = this.configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId("2501") // 传入需要查询流程定义的id
            .singleResult();
        log.info("流程定义ID：{}", processDefinition.getDeploymentId());
        log.info("流程定义名称：{}", processDefinition.getName());
        log.info("流程定义描述：{}", processDefinition.getDescription());
        log.info("ACT_RE_PROCDEF表的id：{}", processDefinition.getId());
    }

    /**
     * 删除流程定义
     */
    @Test
    public void testDeleteDeploy() {
        ProcessEngine processEngine = this.configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 删除部署的流程 第一个参数是 id 如果部署的流程启动了就不允许删除了
        // repositoryService.deleteDeployment("1");
        // 第二个参数是级联删除，如果流程启动了相关的任务一并会被删除掉
        repositoryService.deleteDeployment("1", true);
    }

    @Test
    public void testRunProcess() {
        ProcessEngine processEngine = this.configuration.buildProcessEngine();

        // 我们需要通过RuntimeService来启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 构建流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "张三");
        variables.put("nrOfHolidays", 3);
        variables.put("description", "工作累了");
        // 启动流程实例，任务流转到 Approve or reject request 环节
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

    }

    /**
     * 查询流程实例
     */
    @Test
    public void processInstanceQuery() {
        ProcessEngine processEngine = this.configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 获取流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
            // 5001为ACT_RU_ACTINST表中的PROC_INST_ID_字段
            .processInstanceId("5001").singleResult();

        System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId()); // ACT_RE_PROCDEF表的id
        log.info("流程定义ID：{}", processInstance.getProcessDefinitionId());
        System.out.println("执行ID：" + processInstance.getId()); // EXECUTION_ID
        System.out.println("流程变量：" + runtimeService.getVariables(processInstance.getId()));

        // 查询当前流程任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : taskList) {
            System.out.println(task.getId());
        }
    }
}
