package io.crazydan.jingwei.app.model.manifest;

import io.crazydan.jingwei.app.model.manifest._gen._AppReleasing_Manifest;
import io.nop.api.core.util.INeedInit;

import static io.crazydan.duzhou.framework.commons.ObjectHelper.firstNonNull;

public class AppReleasing_Manifest extends _AppReleasing_Manifest implements INeedInit {

    public AppReleasing_Manifest() {
    }

    @Override
    public void init() {
        // TODO artifactResource 中的 name 必须设置
    }

    /** @return 始终不返回 {@code null} */
    @Override
    public AppReleasing_ArtifactResource getArtifactResource() {
        return firstNonNull(super.getArtifactResource(), AppReleasing_ArtifactResource.NONE);
    }
}
