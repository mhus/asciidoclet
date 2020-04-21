/*
 * Copyright 2013-2018 John Ericksen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.asciidoctor.asciidoclet;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

import com.sun.source.util.DocTrees;

import jdk.javadoc.doclet.DocletEnvironment;

public class AsciidoctorFilteredEnvironment implements DocletEnvironment, AutoCloseable
{
    private final AsciidoctorRenderer renderer;
    private final StandardJavaFileManager fileManager;
    private final AsciiDocTrees asciiDocTrees;
    
    private DocletEnvironment environment;

    AsciidoctorFilteredEnvironment( DocletEnvironment environment, AsciidoctorRenderer renderer )
    {
        this.environment = environment;
        this.renderer = renderer;
        this.fileManager = new AsciidoctorFileManager( renderer, (StandardJavaFileManager) environment.getJavaFileManager() );
        this.asciiDocTrees = new AsciiDocTrees( renderer, fileManager, environment.getDocTrees() );
        
    }

    @Override
    public JavaFileManager getJavaFileManager()
    {
        return fileManager;
    }

    @Override
    public DocTrees getDocTrees()
    {
        return asciiDocTrees;
    }

    @Override
    public void close() throws IOException
    {
        renderer.cleanup();
    }

    @Override
    public Set<? extends Element> getSpecifiedElements() {
        return environment.getSpecifiedElements();
    }

    @Override
    public Set<? extends Element> getIncludedElements() {
        return environment.getIncludedElements();
    }

    @Override
    public boolean isIncluded(Element e) {
        return environment.isIncluded(e);
    }

    @Override
    public Elements getElementUtils() {
        return environment.getElementUtils();
    }

    @Override
    public Types getTypeUtils() {
        return environment.getTypeUtils();
    }

    @Override
    public SourceVersion getSourceVersion() {
        return environment.getSourceVersion();
    }

    @Override
    public ModuleMode getModuleMode() {
        return environment.getModuleMode();
    }

    @Override
    public Kind getFileKind(TypeElement type) {
        return environment.getFileKind(type);
    }

    @Override
    public boolean isSelected(Element e) {
        return environment.isSelected(e);
    }
    
}
