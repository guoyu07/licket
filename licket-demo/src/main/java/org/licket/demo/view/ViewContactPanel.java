package org.licket.demo.view;

import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.core.view.mount.params.MountingParams;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;
import org.licket.semantic.component.button.AbstractSemanticActionLink;
import org.licket.semantic.component.segment.AbstractSemanticUISegment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.model.LicketComponentModel.ofString;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author lukaszgrabski
 */
@LicketMountPoint("/contact/{id}")
public class ViewContactPanel extends AbstractSemanticUISegment<Contact> {

  @Autowired
  private ContactsService contactsService;

  private AbstractSemanticActionLink<Contact> deleteLink;

  public ViewContactPanel(String id) {
    super(id, Contact.class, ofModelObject(new Contact()), fromComponentClass(ViewContactPanel.class));
  }

  @Override
  protected void onInitializeContainer() {
    add(new LicketLabel("name"));
    add(new LicketLabel("description"));
    add(new AbstractLicketList("email", ofString("emails")) {

            @Override
            protected void onInitializeContainer() {
                add(new LicketLabel("value"));
            }

      @Override
      protected Optional<String> keyPropertyName() {
        return Optional.of("id");
      }
    });

    add(new LicketLabel("content"));
    add(new MountedComponentLink("rootLink", ContactsAppRoot.class));
    add(this.deleteLink = new AbstractSemanticActionLink<Contact>("deleteLink", Contact.class) {

      @Override
      protected void onBeforeClick(ComponentFunctionCallback componentFunctionCallback) {
        deleteLink.api(componentFunctionCallback).showLoading(this);
      }

      @Override
      protected void onClick(Contact modelObject) {
        contactsService.deleteContactById(ViewContactPanel.this.getComponentModel().get().getId());
      }

      @Override
      protected void onAfterClick(ComponentActionCallback componentActionCallback) {
        deleteLink.api(componentActionCallback).hideLoading(this);
        componentActionCallback.navigateToMounted(ContactsAppRoot.class);
      }
    });
  }

  @Override
  protected void onBeforeComponentMounted(ComponentFunctionCallback componentFunctionCallback) {
    this.api(componentFunctionCallback).showLoading(this);
  }

  @Override
  protected void onComponentMounted(MountingParams componentMountingParams) {
    Optional<String> contactId = componentMountingParams.getString("id");
    if (!contactId.isPresent()) {
      return;
    }
    Optional<Contact> contactOptional = contactsService.getContactById(contactId.get());
    if (!contactOptional.isPresent()) {
      return;
    }
    setComponentModelObject(contactOptional.get());
  }

  @Override
  protected void onAfterComponentMounted(ComponentActionCallback componentActionCallback) {
    this.api(componentActionCallback).hideLoading(this);
  }
}
