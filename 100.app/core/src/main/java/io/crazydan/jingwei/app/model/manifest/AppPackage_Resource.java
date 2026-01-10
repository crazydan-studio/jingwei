package io.crazydan.jingwei.app.model.manifest;

import io.crazydan.duzhou.framework.commons.StringHelper;
import io.crazydan.jingwei.app.model.manifest._gen._AppPackage_Resource;
import io.nop.core.resource.IResource;
import io.nop.core.resource.VirtualFileSystem;

public class AppPackage_Resource extends _AppPackage_Resource {

    public AppPackage_Resource() {
    }

    /** 根据当前对象的资源位置加载资源 */
    public IResource getResource() {
        String path = StringHelper.normalizePath(getPath());
        String vPath = StringHelper.appendPath(getLocation().getPath(), path);

        return VirtualFileSystem.instance().getResource(vPath, true);
    }
}
