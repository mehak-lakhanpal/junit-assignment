Pre-requisites-

Used mysql for actual implementation
Used h2 for junit testing
Used hashmap for in memory repository testing

Run below mysql scripts into mysql workbench before starting the application-

CREATE SCHEMA `trade`;

INSERT INTO `trade`.`trader` (`trader_id`, `fund`, `name`) VALUES ('1', '800', 'John');
INSERT INTO `trade`.`trader` (`trader_id`, `fund`, `name`) VALUES ('2', '800', 'Ram');


INSERT INTO `trade`.`equity` (`equity_id`, `company`, `name`, `price`, `trader_id`) VALUES ('1', 'Company1', 'Equity1', '500', '1');