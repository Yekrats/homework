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

(deftest file-parse
  (testing
      (is [= '({:last-name "Starkey",
  :first-name "Scotticus",
  :email "yekrats@gmail.com",
  :favorite-color "green",
  :date-of-birth "01/01/2000"}
 {:last-name "McClane",
  :first-name "John",
  :email "jmcclane@nypd.com",
  :favorite-color "brown",
  :date-of-birth "12/24/1960"}
 {:last-name "Gennero",
  :first-name "Holly",
  :email "gennero@nakatomi.com",
  :favorite-color "yellow",
  :date-of-birth "6/20/1960"}
 {:last-name "Gruber",
  :first-name "Hans",
  :email "volksfrei.de",
  :favorite-color "blue",
  :date-of-birth "2/14/1958"})
         (parse-file "./resources/comma-data.csv")])))
