### 内置标签函数

对标签函数的调用形式如下：

```xml
<computed><![CDATA[
  let result = xpl `<fn:GetBirthdayFromIdCardNumber value="${entity.idCardNumber}"/>`;
  return result;
]]></computed>
```

当前可用的内置标签函数如下：

- `<fn:GetBirthdayFromIdCardNumber value="id-card-number"/>`: 从身份证号中获取出生日期。
- `<fn:CalculateAgeByIdCardNumber value="id-card-number"/>`: 通过身份证号计算年龄。
