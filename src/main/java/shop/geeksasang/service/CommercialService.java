package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.geeksasang.dto.commercial.GetCommercialsRes;
import shop.geeksasang.repository.CommercialRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommercialService {

    final private CommercialRepository commercialRepository;

    public List<GetCommercialsRes> getCommercials(){
        return commercialRepository.findAll()
                .stream()
                .map(commercial -> GetCommercialsRes.toDto(commercial))
                .collect(Collectors.toList());
    }
}
