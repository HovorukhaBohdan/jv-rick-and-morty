package mate.academy.rickandmorty.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.exception.EntityNotFoundException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final Random random = new Random();

    @Override
    public CharacterDto getWikiAboutRandomCharacter() {
        long numberOfCharacters = characterRepository.count();
        long randomCharacterId = random.nextLong(numberOfCharacters);

        Character randomCharacter = characterRepository.findById(randomCharacterId).orElseThrow(
                () -> new EntityNotFoundException("Can't get character id" + randomCharacterId)
        );

        return characterMapper.toCharacterDto(randomCharacter);
    }

    @Override
    public CharacterDto findByName(String name) {
        return characterMapper.toCharacterDto(
                characterRepository.findByNameIsContaining(name)
        );
    }
}
