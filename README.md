### 用于信息参数校验

1. **支持的校验类型**
---
   * 是否为空
   * 身份证号是否合法
   * 手机号是否合法
   * 是否为数字字符串
   * 是否为整数
   * 金额判断(可定制)
   * 枚举判断
2. **spring项目使用示例**
---
   * 引入   
   ```
   <bean class="com.lios.check.service.CheckFieldService" id="checkFieldService"/>
   ```
   * 切面类
   ```
   @Component
   @Aspect
   public class CheckFieldAop {
       @Autowired
       CheckFieldService checkFieldService;
   
       @Pointcut("@annotation(com.lios.check.annotation.CheckField)")
       public void pointCut() {
       }
       @Before(value = "pointCut()&& @annotation(checkField)")
       public void before(JoinPoint joinPoint, CheckField checkField) throws Throwable {
           CheckEnum value = checkField.value();
           Object[] args = joinPoint.getArgs();
           if (value.equals(CheckEnum.JSON_OBJECT)) {
               OpenApiJsonObject openApiJsonObject = (OpenApiJsonObject) (args[0]);
               JSONObject jsonObject = openApiJsonObject.getJSONObject("biz_data");
               OpenOrderDTO openOrderDTO = jsonObject.toJavaObject(OpenOrderDTO.class);
               checkFieldService.check(openOrderDTO);
           }
       }
   }
   ```
   * 切点
   ```
       @CheckField(value = CheckEnum.JSON_OBJECT)
       public OpenApiResponse<OpenOrderStatusFeedBackVO> orderPush(JSONObject jsonObject, String appId) {
          ...
          ...
       }
   ```
