package com.brazoft.foundation.soa;

import java.lang.reflect.Type;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class ModuleProcessor {

    private final static Logger           logger = Logger.getLogger(ModuleProcessor.class);

    private final Registry                registry;
    private final ResteasyProviderFactory providerFactory;

    public ModuleProcessor(final Registry registry, final ResteasyProviderFactory providerFactory) {
	this.registry = registry;
	this.providerFactory = providerFactory;
    }

    public Injector process(final Module... modules) {
	final Injector injector = Guice.createInjector(modules);
	processInjector(injector);
	return injector;
    }

    public Injector process(final Stage stage, final Module... modules) {
	final Injector injector = Guice.createInjector(stage, modules);
	processInjector(injector);
	return injector;
    }

    public Injector process(final Iterable<Module> modules) {
	final Injector injector = Guice.createInjector(modules);
	processInjector(injector);
	return injector;
    }

    public Injector process(final Stage stage, final Iterable<Module> modules) {
	final Injector injector = Guice.createInjector(stage, modules);
	processInjector(injector);
	return injector;
    }

    private void processInjector(final Injector injector) {
	for (final Binding<?> binding : injector.getBindings().values()) {
	    final Type type = binding.getKey().getTypeLiteral().getType();
	    if (type instanceof Class) {
		final Class<?> beanClass = (Class)type;
		if (GetRestful.isRootResource(beanClass)) {
		    final ResourceFactory resourceFactory = new GuiceResourceFactory(binding.getProvider(), beanClass);
		    logger.info("registering factory for {0}", beanClass.getName());
		    registry.addResourceFactory(resourceFactory);
		}
		if (beanClass.isAnnotationPresent(Provider.class)) {
		    logger.info("registering provider instance for {0}", beanClass.getName());
		    providerFactory.registerProviderInstance(binding.getProvider().get());
		}
	    }
	}
    }
}
