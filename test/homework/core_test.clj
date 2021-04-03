(ns homework.core-test
  (:require [clojure.test :refer :all]
            [homework.core :refer :all]))

(deftest basic-parse
  (testing
    (is (= (parse-line "Starkey | Scott | name@gmail.com | green | 01/01/2000") ; pipe-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth "01/01/2000"}))

    (is (= (parse-line "Starkey, Scott, name@gmail.com, green, 01/01/2000") ; comma-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth "01/01/2000"}))

    (is (= (parse-line "Starkey Scott name@gmail.com green 01/01/2000") ; space-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth "01/01/2000"}))


    ))
