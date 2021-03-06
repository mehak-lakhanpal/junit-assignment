package com.nagarro.trade.repository;


import com.nagarro.trade.entity.Trader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class TraderInMemoryRepository implements TraderRepository{
    public static final Map<Long,Trader> db = new HashMap<>();

    @Override
    public Trader save(Trader trader){
        db.put(trader.getTraderId(),trader);
        return trader;
    }

    @Override
    public Optional<Trader> findById(Long traderId){
        return Optional.of(db.get(traderId));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Trader> findAll() {
        return null;
    }

    @Override
    public List<Trader> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Trader> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Trader> findAllById(Iterable<Long> longs) {
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
    public void delete(Trader entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Trader> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Trader> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Trader> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Trader> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Trader> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Trader getOne(Long aLong) {
        return null;
    }

    @Override
    public Trader getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Trader> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Trader> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Trader> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Trader> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Trader> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Trader> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Trader, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
