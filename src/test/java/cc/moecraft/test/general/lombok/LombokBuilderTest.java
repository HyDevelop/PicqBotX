package cc.moecraft.test.general.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-03-24!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-24 10:19
 */
public class LombokBuilderTest
{
    public static void main(String[] args)
    {
        System.out.println("---------------");
        System.out.println(new ClassWithBuilder().toBuilder().build());
        System.out.println("---------------");
        System.out.println(ClassWithBuilder.builder().build());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    @ToString
    private static class ClassWithBuilder
    {
        @Builder.Default
        private SayHiOnConstruct field = new SayHiOnConstruct();
    }

    private static class SayHiOnConstruct
    {
        public SayHiOnConstruct()
        {
            System.out.println("Hi");
        }
    }
}
