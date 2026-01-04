```xml
<erd>
    <dicts>
        <dict name="app-status" displayName="应用状态">
            <description>应用的状态，包括开发中、已启用和已禁用</description>
            <options>
                <option value="010" code="DEVELOPING" displayName="开发中">
                    <description>应用正在开发中，尚未激活任何版本</description>
                </option>
                <option value="020" code="ENABLED" displayName="已启用">
                    <description>应用已启用，用户可以正常使用</description>
                </option>
                <option value="030" code="DISABLED" displayName="已禁用">
                    <description>应用已被禁用，用户只能编辑基本信息</description>
                </option>
            </options>
        </dict>
        <dict name="app-version-status" displayName="版本状态">
            <description>应用版本的状态，包括开发中和已发布</description>
            <options>
                <option value="010" code="DEVELOPING" displayName="开发中">
                    <description>版本正在开发中，可以修改和删除</description>
                </option>
                <option value="020" code="PUBLISHED" displayName="已发布">
                    <description>版本已发布，不可修改和删除，可以被激活</description>
                </option>
            </options>
        </dict>
    </dicts>
    <entities>
        <entity name="App" displayName="应用" db:estimatedRowCount="1000">
            <description>个人数字资产应用，用户可以创建和管理自己的本地应用</description>
            <orm:unique-keys>
                <key name="uk_code">code</key>
            </orm:unique-keys>
            <attrs>
                <attr name="code" displayName="唯一标识" mandatory="true" stdDomain="uuidv7" insertable="true" updatable="false" queryable="true" sortable="true">
                    <description>应用的唯一标识符，采用 UUIDv7 格式，系统自动生成，全局唯一。例如：018f1234-5678-9abc-def0-123456789abc</description>
                </attr>
                <attr name="name" displayName="名字" mandatory="true" stdDomain="string" minLength="1" maxLength="200" insertable="true" updatable="true" queryable="true" sortable="true">
                    <description>应用的名称，最长 200 个字符，不同应用可以重名</description>
                </attr>
                <attr name="icon" displayName="图标" mandatory="false" stdDomain="file" insertable="true" updatable="true" queryable="false" sortable="false" maxFileSize="2M" allowedFileTypes="image/jpeg,image/png,image/webp,image/svg+xml,image/x-icon">
                    <description>应用的图标文件，支持 JPEG、PNG、WebP、SVG 和 ICO 格式，不支持 GIF 格式</description>
                </attr>
                <attr name="status" displayName="状态" mandatory="true" stdDomain="string" dict="app-status" insertable="true" updatable="true" queryable="true" sortable="true" defaultValue="010">
                    <description>应用的当前状态，初始为开发中。只有已启用状态的应用才能被用户使用</description>
                </attr>
                <attr name="description" displayName="说明" mandatory="false" stdDomain="markdown" insertable="true" updatable="true" queryable="true" sortable="false">
                    <description>应用的详细说明，支持 Markdown 格式。可以描述应用的功能、用途等信息</description>
                </attr>
            </attrs>
        </entity>
        <entity name="AppVersion" displayName="应用版本" db:estimatedRowCount="5000">
            <description>应用的版本信息，包含版本的设计说明、模型定义和 UI 代码</description>
            <orm:unique-keys>
                <key name="uk_app_dev_code">appId,devCode</key>
            </orm:unique-keys>
            <attrs>
                <attr name="appId" displayName="所属应用" mandatory="true" stdDomain="string" insertable="true" updatable="false" queryable="true" sortable="true">
                    <description>所属应用的主键 ID</description>
                </attr>
                <attr name="name" displayName="版本号" mandatory="false" stdDomain="string" insertable="true" updatable="true" queryable="true" sortable="true">
                    <description>版本号，采用 x.y.z 三段格式，每段最多 3 位数字。例如：1.0.0 或 2.15.3</description>
                </attr>
                <attr name="devCode" displayName="开发代码" mandatory="true" stdDomain="string" insertable="true" updatable="false" queryable="true" sortable="true">
                    <description>版本开发代码，系统自动生成，与所属应用组成复合唯一键</description>
                </attr>
                <attr name="status" displayName="状态" mandatory="true" stdDomain="string" dict="app-version-status" insertable="true" updatable="true" queryable="true" sortable="true" defaultValue="010">
                    <description>版本的状态，初始为开发中。只有已发布状态才能被激活</description>
                </attr>
                <attr name="description" displayName="说明" mandatory="false" stdDomain="text" insertable="true" updatable="true" queryable="true" sortable="false">
                    <description>版本说明，描述版本实现的功能、特色或变更情况</description>
                </attr>
                <attr name="appRequirements" displayName="应用功能设计需求" mandatory="false" stdDomain="text" insertable="true" updatable="true" queryable="true" sortable="false">
                    <description>应用整体的功能设计描述，提交给 AI 生成模型和代码</description>
                </attr>
                <attr name="modelRequirements" displayName="业务模型设计需求" mandatory="false" stdDomain="text" insertable="true" updatable="true" queryable="true" sortable="false">
                    <description>应用的业务模型结构和约束描述，提交给 AI 生成模型定义</description>
                </attr>
                <attr name="uiRequirements" displayName="UI设计需求" mandatory="false" stdDomain="text" insertable="true" updatable="true" queryable="true" sortable="false">
                    <description>应用的 UI 设计风格和要求描述，提交给 AI 生成 UI 代码</description>
                </attr>
                <attr name="modelDefs" displayName="业务模型定义" mandatory="false" stdDomain="xml" insertable="true" updatable="true" queryable="true" sortable="false">
                    <description>AI 生成的业务模型结构定义，采用 XML 格式</description>
                </attr>
                <attr name="uiDefs" displayName="UI代码" mandatory="false" stdDomain="html" insertable="true" updatable="true" queryable="true" sortable="false">
                    <description>AI 生成的 UI 代码，采用 HTML 格式</description>
                </attr>
                <attr name="aiModelVendor" displayName="AI模型供应商" mandatory="false" stdDomain="string" insertable="true" updatable="true" queryable="true" sortable="true">
                    <description>使用的 AI 模型供应商标识，如 OpenAI、DeepSeek 等</description>
                </attr>
                <attr name="aiModelName" displayName="AI模型名" mandatory="false" stdDomain="string" insertable="true" updatable="true" queryable="true" sortable="true">
                    <description>使用的 AI 模型标识，如 gpt-4、deepseek-coder 等</description>
                </attr>
            </attrs>
        </entity>
    </entities>
    <relations>
        <relation name="app_versions" source="AppVersion" sourceProp="appId" target="App" targetProp="versions" targetPropDisplayName="版本列表"/>
        <relation name="app_active_version" source="App" sourceProp="activeVersion" target="AppVersion"/>
        <relation name="version_derived_from" source="AppVersion" sourceProp="derivedFrom" target="AppVersion"/>
    </relations>
</erd>
```
