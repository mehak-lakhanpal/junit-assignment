package com.nagarro.trade.repository;

import com.nagarro.trade.entity.Equity;
import com.nagarro.trade.entity.Trader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class EquityInMemoryRepository implements EquityRepository{

    public static final Map<Long, Equity> db = new HashMap<>();

    @Override
    public Equity save(Equity equity){
        db.put(equity.getEquityId(),equity);
        return equity;
    }

    @Override
    public Optional<Equity> findById(Long equityId){
        return Optional.of(db.get(equityId));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Equity> findAll() {
        return null;
    }

    @Override
    public List<Equity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Equity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Equity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Equity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Equity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Equity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Equity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Equity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Equity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Equity getOne(Long aLong) {
        return null;
    }

    @Override
    public Equity getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Equity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Equity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Equity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Equity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Equity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Equity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Equity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
