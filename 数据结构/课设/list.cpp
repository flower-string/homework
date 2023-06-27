#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <list>

using namespace std;

struct Term {
    int coef;
    int exp;
    Term(int c, int e) : coef(c), exp(e) {}
};

list<Term> readPolynomial() {
    list<Term> p;
    int coef, exp;
    while (cin >> coef >> exp) {
        if (exp < 0) {
            cerr << "Invalid exponent " << exp << endl;
            return list<Term>();
        }
        if (coef != 0) {
            p.push_back(Term(coef, exp));
        }
    }
    return p;
}

list<Term> addPoly(const list<Term>& p, const list<Term>& q) {
    list<Term> result;
    auto p_it = p.begin(), q_it = q.begin();
    while (p_it != p.end() && q_it != q.end()) {
        if (p_it->exp == q_it->exp) {
            int coefSum = p_it->coef + q_it->coef;
            if (coefSum != 0) {
                result.push_back(Term(coefSum, p_it->exp));
            }
            p_it++;
            q_it++;
        }
        else if (p_it->exp > q_it->exp) {
            result.push_back(*p_it);
            p_it++;
        }
        else {
            result.push_back(*q_it);
            q_it++;
        }
    }
    while (p_it != p.end()) {
        result.push_back(*p_it);
        p_it++;
    }
    while (q_it != q.end()) {
        result.push_back(*q_it);
        q_it++;
    }
    return result;
}

list<Term> subtractPoly(const list<Term>& p, const list<Term>& q) {
    list<Term> result;
    auto p_it = p.begin(), q_it = q.begin();
    while (p_it != p.end() && q_it != q.end()) {
        if (p_it->exp == q_it->exp) {
            int coefDiff = p_it->coef - q_it->coef;
            if (coefDiff != 0) {
                result.push_back(Term(coefDiff, p_it->exp));
            }
            p_it++;
            q_it++;
        }
        else if (p_it->exp > q_it->exp) {
            result.push_back(*p_it);
            p_it++;
        }
        else {
            result.push_back(Term(-q_it->coef, q_it->exp));
            q_it++;
        }
    }
    while (p_it != p.end()) {
        result.push_back(*p_it);
        p_it++;
    }
    while (q_it != q.end()) {
        result.push_back(Term(-q_it->coef, q_it->exp));
        q_it++;
    }
    return result;
}

list<Term> multiplyPoly(const list<Term>& p, const list<Term>& q) {
    list<Term> result;
    for (auto p_it = p.begin(); p_it != p.end(); p_it++) {
        for (auto q_it = q.begin(); q_it != q.end(); q_it++) {
            int coef = p_it->coef * q_it->coef;
            int exp = p_it->exp + q_it->exp;
            result.push_back(Term(coef, exp));
        }
    }
    result.sort([](const Term& a, const Term& b) { return a.exp > b.exp; });
    auto it = result.begin();
    while (it != result.end()) {
        auto curr = it;
        it++;
        if (it != result.end() && it->exp == curr->exp) {
            it->coef += curr->coef;
            it = result.erase(curr);
        }
    }
    return result;
}

void printPoly(const list<Term>& p, bool ascending, ostream& out) {
    if (p.empty()) {
        out << "0" << endl;
        return;
    }
    list<Term> terms = p;
    if (ascending) {
        terms.sort([](const Term& a, const Term& b) { return a.exp < b.exp; });
    }
    else {
        terms.sort([](const Term& a, const Term& b) { return a.exp > b.exp; });
    }
    auto it = terms.begin();
    if (it->exp == 0) {
        out << it->coef;
        it++;
    }
    while (it != terms.end()) {
        if (it->coef > 0) {
            out << " + ";
        }
        else {
            out << " - ";
        }
        if (abs(it->coef) != 1 || it->exp == 0) {
            out << abs(it->coef);
        }
        if (it->exp > 0) {
            out << "x";
            if (it->exp > 1) {
                out << "^" << it->exp;
            }
        }
        it++;
    }
    out << endl;
}

int main() {
    cout << "Enter first polynomial:" << endl;
    list<Term> p = readPolynomial();
    cout << "Enter second polynomial:" << endl;
    list<Term> q = readPolynomial();

    cout << "Sum of the polynomials is:" << endl;
    list<Term> sum = addPoly(p, q);
    printPoly(sum, false, cout);

    cout << "Difference of the polynomials is:" << endl;
    list<Term> difference = subtractPoly(p, q);
    printPoly(difference, false, cout);

    cout << "Product of the polynomials is:" << endl;
    list<Term> product = multiplyPoly(p, q);
    printPoly(product, false, cout);

    ofstream outputFile("output.txt");
    cout << "Polynomials written to output.txt" << endl;
    outputFile << "Sum of the polynomials is:" << endl;
    printPoly(sum, false, outputFile);
    outputFile << "Difference of the polynomials is:" << endl;
    printPoly(difference, false, outputFile);
    outputFile << "Product of the polynomials is:" << endl;
    printPoly(product, false, outputFile);
    outputFile.close();

    return 0;
}
