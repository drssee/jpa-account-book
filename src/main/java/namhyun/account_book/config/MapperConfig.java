package namhyun.account_book.config;

import namhyun.account_book.domain.*;
import namhyun.account_book.dto.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        initCustomTypeMap(modelMapper);
        return modelMapper;
    }

    private void initCustomTypeMap(ModelMapper modelMapper) {
        modelMapper.createTypeMap(AccountBookDto.class, AccountBook.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberConverter()).map(source.getMemberDto(), destination.getMember());
                    }
                });
        modelMapper.createTypeMap(AccountBook.class, AccountBookDto.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberDtoConverter()).map(source.getMember(), destination.getMemberDto());
                    }
                });
        modelMapper.createTypeMap(ConfigDto.class, Config.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberConverter()).map(source.getMemberDto(), destination.getMember());
                    }
                });
        modelMapper.createTypeMap(Config.class, ConfigDto.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberDtoConverter()).map(source.getMember(), destination.getMemberDto());
                    }
                });
        modelMapper.createTypeMap(SendDto.class, Send.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberConverter()).map(source.getMemberDto(), destination.getMember());
                    }
                });
        modelMapper.createTypeMap(Send.class, SendDto.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberDtoConverter()).map(source.getMember(), destination.getMemberDto());
                    }
                });
        modelMapper.createTypeMap(StatisticsDto.class, Statistics.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberConverter()).map(source.getMemberDto(), destination.getMember());
                    }
                });
        modelMapper.createTypeMap(Statistics.class, StatisticsDto.class)
                .addMappings(new PropertyMap<>() {
                    @Override
                    protected void configure() {
                        using(toMemberDtoConverter()).map(source.getMember(), destination.getMemberDto());
                    }
                });
    }

    private Converter<MemberDto, Member> toMemberConverter() {
        return context -> modelMapper().map(context.getSource(), Member.class);
    }

    private Converter<Member, MemberDto> toMemberDtoConverter() {
        return context -> modelMapper().map(context.getSource(), MemberDto.class);
    }
}
