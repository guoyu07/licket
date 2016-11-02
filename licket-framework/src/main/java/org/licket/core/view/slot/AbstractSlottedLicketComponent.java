package org.licket.core.view.slot;

import org.licket.core.model.LicketModel;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.ComponentView;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.render.ComponentRenderingContext;

/**
 * @author activey
 */
public class AbstractSlottedLicketComponent<T> extends AbstractLicketComponent<T> {

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass) {
        super(id, modelClass);
    }

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass, LicketModel<T> componentModel) {
        super(id, modelClass, componentModel);
    }

    public AbstractSlottedLicketComponent(String id, Class<T> modelClass, LicketModel<T> componentModel, ComponentView view) {
        super(id, modelClass, componentModel, view);
    }

    public final void slot(LicketComponent<?> component) {

    }

    public final void slot(String slotName, LicketComponent<?> component) {

    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {

    }
}