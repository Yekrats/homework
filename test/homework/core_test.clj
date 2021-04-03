(ns homework.core-test
  (:require [clojure.test :refer :all]
            [homework.core :refer :all]))

(deftest basic-parse
  (testing
    (is (= (parse-line "Starkey | Scott | name@gmail.com | green | 01/01/2000") ; pipe-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))

    (is (= (parse-line "Starkey, Scott, name@gmail.com, green, 01/01/2000") ; comma-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))

    (is (= (parse-line "Starkey Scott name@gmail.com green 01/01/2000") ; space-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))
    ))

 (deftest file-parse
   (testing
       (is [= '({:last-name "Starkey",
                  :first-name "Scotticus",
                  :email "yekrats@gmail.com",
                  :favorite-color "green",
                  :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}
                {:last-name "McClane",
                  :first-name "John",
                  :email "jmcclane@nypd.com",
                  :favorite-color "brown",
                  :date-of-birth #inst "1960-12-24T05:00:00.000-00:00"}
                {:last-name "Gennero",
                  :first-name "Holly",
                  :email "gennero@nakatomi.com",
                  :favorite-color "yellow",
                  :date-of-birth #inst "1960-06-20T05:00:00.000-00:00"}
                {:last-name "Gruber",
                  :first-name "Hans",
                  :email "volksfrei.de",
                  :favorite-color "blue",
                  :date-of-birth #inst "1958-02-14T06:00:00.000-00:00"})
              (parse-file "./resources/comma-data.csv")])))
