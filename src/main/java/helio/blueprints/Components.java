package helio.blueprints;

import java.util.HashMap;
import java.util.Map;

import helio.blueprints.components.DataHandler;
import helio.blueprints.components.DataProvider;
import helio.blueprints.components.MappingFunctions;
import helio.blueprints.components.MappingReader;
import helio.blueprints.exceptions.ExtensionNotFoundException;

public class Components {

	public static Map<String, DataProvider> dataProviders = new HashMap<>();
	public static Map<String, DataHandler> dataHandlers = new HashMap<>();
	public static Map<String, MappingReader> mappingReaders = new HashMap<>();
	public static Map<String, MappingFunctions> mappingFunctions = new HashMap<>();


	public static final String EXTENSION_TYPE_PROVIDER = "DataProvider";
	public static final String EXTENSION_TYPE_HANDLER = "DataHandler";
	public static final String EXTENSION_TYPE_READER = "MappingReader";
	public static final String EXTENSION_TYPE_FUNCTION = "MappingFunctions";




	private Components() {
		super();
	}


	// Load Methods

	public static void registerComponent(String source, String clazz, String type) throws ExtensionNotFoundException {
		String name = clazz.substring(clazz.lastIndexOf('.')+1);

		if (type.equals(EXTENSION_TYPE_PROVIDER)) {
			DataProvider provider = buildDataProvider(source, clazz);
			dataProviders.put(name, provider); //TODO: if name exists throw exception it could happen that two jars have a class with the same name
		}else if (type.equals(EXTENSION_TYPE_HANDLER)) {
			DataHandler handler = buildDataHandler(source, clazz);
			dataHandlers.put(name, handler); //TODO:  if name exists throw exception it could happen that two jars have a class with the same name

		}else if (type.equals(EXTENSION_TYPE_FUNCTION)) {
			MappingFunctions function = buildMappingFunctions(source, clazz);
			mappingFunctions.put(name, function); //TODO:  if name exists throw exception it could happen that two jars have a class with the same name

		}else if (type.equals(EXTENSION_TYPE_READER)) {
			MappingReader reader = buildMappingReader(source, clazz);
			mappingReaders.put(name, reader);  //TODO:  if name exists throw exception it could happen that two jars have a class with the same name

		}else {
			//TODO: THROW AN EXCEPTION
		}

	}




	// Building methods


	private static DataProvider buildDataProvider(String source, String clazz) throws ExtensionNotFoundException {
		DataProvider dataProviderPlugin = null;
		try {
			ComponentsLoader<DataProvider> loader = new ComponentsLoader<>();
			dataProviderPlugin = loader.loadClass(source, clazz, DataProvider.class);
		} catch (Exception e) {
			throw new ExtensionNotFoundException(e.toString());
		}
		return dataProviderPlugin;
	}



	private static DataHandler buildDataHandler(String source, String clazz) throws ExtensionNotFoundException {
		DataHandler dataHandlerPlugin = null;
		try {
			ComponentsLoader<DataHandler> loader = new ComponentsLoader<>();
			dataHandlerPlugin = loader.loadClass(source, clazz, DataHandler.class);
		} catch (Exception e) {
			throw new ExtensionNotFoundException(e.toString());
		}
		return dataHandlerPlugin;
	}



	private static MappingFunctions buildMappingFunctions(String source, String clazz) throws ExtensionNotFoundException {
		MappingFunctions MappingFunctionsPlugins = null;
		try {
			ComponentsLoader<MappingFunctions> loader = new ComponentsLoader<>();
			MappingFunctionsPlugins = loader.loadClass(source, clazz, MappingFunctions.class);
		} catch (Exception e) {
			throw new ExtensionNotFoundException(e.toString());
		}
		return MappingFunctionsPlugins;
	}


	private static MappingReader buildMappingReader(String source, String clazz) throws ExtensionNotFoundException {
		MappingReader materialiserTranslatorPlugins = null;
		try {
			ComponentsLoader<MappingReader> loader = new ComponentsLoader<>();
			materialiserTranslatorPlugins = loader.loadClass(source, clazz, MappingReader.class);
		} catch (Exception e) {
			throw new ExtensionNotFoundException(e.toString());
		}
		return materialiserTranslatorPlugins;
	}

}
