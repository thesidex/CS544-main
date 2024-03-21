package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Scanner;
import org.springframework.stereotype.Repository;

@Repository
public interface ScannerRepository extends BaseRepository<Scanner, Long> {
}
