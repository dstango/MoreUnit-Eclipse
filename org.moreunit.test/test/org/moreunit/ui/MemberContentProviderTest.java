package org.moreunit.ui;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.junit.Before;
import org.junit.Test;

public class MemberContentProviderTest
{

    private Set<IType> types;
    private Set<IMethod> methods;

    @Before
    public void setUp()
    {
        types = new LinkedHashSet<IType>();
        methods = new LinkedHashSet<IMethod>();
    }

    @Test
    public void getElements_should_return_empty_list_when_no_type_nor_method_is_provided()
    {
        Object[] elements = new MemberContentProvider(types, methods, null).getElements(null);
        assertThat(elements).isEmpty();
    }

    @Test
    public void getElements_should_return_types_when_called_with_types()
    {
        types.add(mockType("type2"));
        types.add(mockType("type3"));
        types.add(mockType("type1"));

        MemberContentProvider contentProvider = new MemberContentProvider(types, methods, null);

        Object[] elements = contentProvider.getElements(null);
        assertThat(elements).hasSize(3);
        assertThat(((IType) elements[0]).getElementName()).isEqualTo("type1");
        assertThat(((IType) elements[1]).getElementName()).isEqualTo("type2");
        assertThat(((IType) elements[2]).getElementName()).isEqualTo("type3");
    }

    @Test
    public void should_build_elements_with_children_when_called_with_types_and_methods()
    {
        IType type2 = mockType("type2");
        types.add(type2);

        IType type1 = mockType("type1");
        types.add(type1);

        methods.add(mockMethod(type1, "method1B"));
        methods.add(mockMethod(type2, "method2A"));
        methods.add(mockMethod(type1, "method1A"));

        MemberContentProvider contentProvider = new MemberContentProvider(types, methods, null);

        Object[] elements = contentProvider.getElements(null);
        assertThat(elements).hasSize(2);
        assertThat(((IType) elements[0]).getElementName()).isEqualTo("type1");
        assertThat(((IType) elements[1]).getElementName()).isEqualTo("type2");

        Object[] children1 = contentProvider.getChildren(elements[0]);
        assertThat(children1).hasSize(2);
        assertThat(((IMethod) children1[0]).getElementName()).isEqualTo("method1A");
        assertThat(((IMethod) children1[1]).getElementName()).isEqualTo("method1B");

        Object[] children2 = contentProvider.getChildren(elements[1]);
        assertThat(children2).hasSize(1);
        assertThat(((IMethod) children2[0]).getElementName()).isEqualTo("method2A");
    }

    @Test
    public void should_detect_types_when_only_method_is_given()
    {
        IType type1 = mockType("type1");
        types.add(type1);

        IType type2 = mockType("type2"); // not added to type set
        methods.add(mockMethod(type2, "method2A"));

        MemberContentProvider contentProvider = new MemberContentProvider(types, methods, null);

        Object[] elements = contentProvider.getElements(null);
        assertThat(elements).hasSize(2);
        assertThat(((IType) elements[0]).getElementName()).isEqualTo("type1");
        assertThat(((IType) elements[1]).getElementName()).isEqualTo("type2");

        Object[] children2 = contentProvider.getChildren(elements[1]);
        assertThat(children2).hasSize(1);
        assertThat(((IMethod) children2[0]).getElementName()).isEqualTo("method2A");
    }

    @Test
    public void should_build_with_types_with_and_without_methods()
    {
        IType type3 = mockType("type3");
        IType type1 = mockType("type1");

        methods.add(mockMethod(type1, "method1B"));
        methods.add(mockMethod(type3, "method3A"));
        methods.add(mockMethod(type1, "method1A"));

        IType type2 = mockType("type2");
        types.add(type2);

        MemberContentProvider contentProvider = new MemberContentProvider(types, methods, null);

        Object[] elements = contentProvider.getElements(null);
        assertThat(elements).hasSize(3);
        assertThat(((IType) elements[0]).getElementName()).isEqualTo("type2");
        assertThat(((IType) elements[1]).getElementName()).isEqualTo("type1");
        assertThat(((IType) elements[2]).getElementName()).isEqualTo("type3");

        Object[] children1 = contentProvider.getChildren(elements[1]);
        assertThat(children1).hasSize(2);
        assertThat(((IMethod) children1[0]).getElementName()).isEqualTo("method1A");
        assertThat(((IMethod) children1[1]).getElementName()).isEqualTo("method1B");

        Object[] children3 = contentProvider.getChildren(elements[2]);
        assertThat(children3).hasSize(1);
        assertThat(((IMethod) children3[0]).getElementName()).isEqualTo("method3A");
    }

    private IType mockType(String typeName)
    {
        IType mock = mock(IType.class);
        when(mock.getElementName()).thenReturn(typeName);
        return mock;
    }

    private IMethod mockMethod(IType declaringType, String methodName)
    {
        IMethod mock = mock(IMethod.class);
        when(mock.getElementName()).thenReturn(methodName);
        when(mock.getDeclaringType()).thenReturn(declaringType);
        return mock;
    }
}
