package org.moreunit.mock.it;

import static com.google.common.collect.Collections2.transform;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.moreunit.mock.BindingOverridingRule;
import org.moreunit.mock.model.Category;
import org.moreunit.mock.templates.MockingTemplateStore;

import com.google.common.base.Function;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;

public class SmokeTest
{
    @Rule
    public BindingOverridingRule bindingOverridingRule = new BindingOverridingRule(new AbstractModule()
    {
        @Override
        protected void configure()
        {
            System.err.println("test module loaded");
        }
    });

    @Inject
    private MockingTemplateStore templateStore;

    @Test
    public void should_have_loaded_templates() throws Exception
    {
        assertThat(templateStore).isNotNull();

        assertThat(transform(templateStore.getCategories(), new IdCollector()))
                .contains("org.moreunit.mock.easymock", "org.moreunit.mock.mockito");
    }

    private static class IdCollector implements Function<Category, String>
    {
        public String apply(Category category)
        {
            return category.id();
        }
    }
}
