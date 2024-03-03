

UPDATE share SET twitter = REPLACE(twitter, '@@', '@') WHERE twitter LIKE '@@%';