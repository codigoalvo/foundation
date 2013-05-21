/**
 * Copyright (C) 2009-2012 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brazoft.foundation.gwt.client.ui;

import com.brazoft.foundation.gwt.client.ui.Button.ButtonOptions;
import com.brazoft.foundation.gwt.client.ui.api.Bootstrap;
import com.brazoft.foundation.gwt.client.ui.api.NativeEvent;
import com.brazoft.foundation.gwt.client.component.ElementResolver;
import com.brazoft.foundation.gwt.client.component.api.Component;
import com.brazoft.foundation.gwt.client.component.api.HasText;
import com.brazoft.foundation.gwt.client.event.api.EventHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public final class Modal
    extends NativeEvent<Modal> {

    private ModalHeader header = new ModalHeader();

    private ModalBody   body   = new ModalBody();

    private ModalFooter footer = new ModalFooter();

    public Modal() {
	super(ElementResolver.div());
	String id = ElementResolver.document().createUniqueId();
	this.id(id).className("modal hide fade in").attribute("role", "dialog").attribute("aria-labelledby", id + "_label").attribute("aria-hidden",
	                                                                                                                              "true").attribute("data-keyboard",
	                                                                                                                                                "true");
    }

    public Modal statical() {
	return this.attribute("data-backdrop", "static");
    }

    public Button trigger(String label) {
	return new Button(ButtonOptions.ANCHOR).attribute("href", "#" + this.getId()).attribute("role", "button").attribute("data-toggle",
	                                                                                                                    "modal").text(label);
    }

    public Modal heading(String text) {
	return this.add(this.header.text(text));
    }

    public ModalBody body() {
	this.add(this.body);

	return this.body;
    }

    public ModalFooter footer() {
	this.add(this.footer);

	return this.footer;
    }

    public static class ModalBody
	extends Bootstrap<ModalBody> {

	public ModalBody() {
	    super(ElementResolver.div());
	    this.className("modal-body");
	}

	@Override
	public ModalBody add(Widget add) {
	    return super.add(add);
	}
    }

    public static class ModalFooter
	extends Bootstrap<ModalFooter> {

	public ModalFooter() {
	    super(ElementResolver.div());
	    this.className("modal-footer");
	}

	@Override
	public ModalFooter add(Widget add) {
	    return super.add(add);
	}
    }

    public static class ModalHeader
	extends Bootstrap<ModalHeader>
	implements HasText<ModalHeader> {

	private Heading heading = new Heading(3);

	public ModalHeader() {
	    super(ElementResolver.div());
	    this.className("modal-header");
	    this.add(new Button().className("close").attribute("data-dismiss", "modal").attribute("aria-hidden", "true").text("×")).add(this.heading);
	}

	@Override
	public String getText() {
	    return this.heading.getText();
	}

	@Override
	public ModalHeader text(String text) {
	    Component.Util.setHTML(this.heading, text);
	    return this;
	}
    }

    public Modal onShow(EventHandler event) {
	return this.addEvent("show", event);
    }

    public Modal whenShown(EventHandler event) {
	return this.addEvent("shown", event);
    }

    public Modal onHide(EventHandler event) {
	return this.addEvent("hide", event);
    }

    public Modal whenHidden(EventHandler event) {
	return this.addEvent("hidden", event);
    }

    public Modal show() {
	if (!this.isAttached()) {
	    RootPanel.get().add(this);
	}

	this.jsFunction(this.getId(), "show");

	return this;
    }

    public Modal hide() {
	this.jsFunction(this.getId(), "hide");
	return this;
    }

    private native void jsFunction(String id, String method)/*-{
	                                                    $wnd.$("#" + id).modal(method);
	                                                    }-*/;

    @Override
    protected void registerNativeEvent(Iterable<String> types) {
	for (String type : types) {
	    this.registerEvent(this, type, this.getId());
	}
    }

    private native void registerEvent(Modal widget, String type, String id) /*-{
	                                                                    $wnd.$("#" + id).on(type, function () {
	                                                                    widget.@com.brazoft.foundation.gwt.client.component.api.Component::fireEvent(Ljava/lang/String;)(type);
	                                                                    });
	                                                                    }-*/;
}