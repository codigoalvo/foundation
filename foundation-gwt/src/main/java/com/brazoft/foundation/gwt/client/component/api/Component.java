/**
 * Copyright (C) 2009-2012 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brazoft.foundation.gwt.client.component.api;

import com.brazoft.foundation.gwt.client.component.Point;
import com.brazoft.foundation.gwt.client.component.Style;
import com.brazoft.foundation.gwt.client.event.api.AttachHandler;
import com.brazoft.foundation.gwt.client.event.api.DetachHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("unchecked")
public abstract class Component<C extends Component<C>> extends Composite<C>
{
	public Component(Element element)
	{
		super(element);
	}
	
	public static class Util
	{
		public static <W extends Widget> W setId(W widget, String id)
		{
			widget.getElement().setId(id);
			return widget;
		}
		
		public static <W extends Widget> String getId(W widget)
		{
			return widget.getElement().getId();
		}
		
		public static String getHTML(Widget widget)
		{
			return widget.getElement().getInnerHTML();
		}
		
		public static <W extends Widget> W setHTML(W widget, String html)
		{
			widget.getElement().setInnerHTML(html);
			
			return widget;
		}
		
		public static <W extends Widget> double innerWidth(W widget)
		{
			double left = computeInnerLeft(widget);
			double right = computeInnerRight(widget);
			
			return (widget.getOffsetWidth() - left) - right;
		}
		
		public static <W extends Widget> double computeInnerLeft(W widget)
		{
			Style<W> style = Style.<W>get(widget);
			
			return style.computePropertyInt("padding-left") + style.computePropertyInt("margin-left") + style.computePropertyInt("border-left");
		}
		
		public static <W extends Widget> double computeInnerRight(W widget)
		{
			Style<W> style = Style.<W>get(widget);
			
			return style.computePropertyInt("padding-right") + style.computePropertyInt("margin-right") + style.computePropertyInt("border-right");
		}
		
		public static <W extends Widget> W responsiveBehavior(final ResponsiveComponent<W> component, final Widget container)
		{
			component.onAttach(new AttachHandler()
			{
				@Override
				public void onAttach(AttachEvent event)
				{
					component.adaptSize(container);
				}
			});
			
			final HandlerRegistration resizeHandler = Window.addResizeHandler(new ResizeHandler()
			{
				@Override
				public void onResize(ResizeEvent event)
				{
					component.adaptSize(container);
				}
			});
			
			component.onDetach(new DetachHandler()
			{
				@Override
				protected void onDetach(AttachEvent event)
				{
					resizeHandler.removeHandler();
				}
			});
			
			return (W) component;
		}
	}
}