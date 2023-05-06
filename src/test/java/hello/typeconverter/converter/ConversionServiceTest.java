package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

public class ConversionServiceTest {

    //DefaultConversionService 를 통해 컨버터를 등록하고
    //사용하는 입장에서는 conversionService 에 대고 데이터넣고 반환할 타입을 넣으면 된다.
    //conversionService가 적적한 컨버터를 찾아 실행해준다.
    @Test
    void conversionService() {
        //등록
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        //사용
        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        assertThat(conversionService.convert(10, String.class)).isEqualTo("10");

        IpPort ipPort = conversionService.convert("127.0.0.7:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.7",8080));

        String result = conversionService.convert(new IpPort("127.0.0.7", 8080), String.class);
        assertThat(result).isEqualTo("127.0.0.7:8080");
    }
}
