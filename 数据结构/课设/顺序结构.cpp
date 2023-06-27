#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
using namespace std;

struct Term {
    int degree;     // ָ��
    double coeff;   // ϵ��
};

// һԪ����ʽ�ӷ�
vector<Term> add_poly(const vector<Term>& A, const vector<Term>& B) {
    vector<Term> C;
    int i = 0, j = 0;
    while (i < A.size() && j < B.size()) {
        if (A[i].degree == B[j].degree) {
            double coeff = A[i].coeff + B[j].coeff;
            if (coeff != 0) {
                C.push_back({A[i].degree, coeff});
            }
            i++;
            j++;
        } else if (A[i].degree < B[j].degree) {
            C.push_back(A[i]);
            i++;
        } else {
            C.push_back(B[j]);
            j++;
        }
    }
    while (i < A.size()) {
        C.push_back(A[i]);
        i++;
    }
    while (j < B.size()) {
        C.push_back(B[j]);
        j++;
    }
    return C;
}

// һԪ����ʽ����
vector<Term> subtract_poly(const vector<Term>& A, const vector<Term>& B) {
    vector<Term> neg_B = B;
    for (int i = 0; i < neg_B.size(); i++) {
        neg_B[i].coeff *= -1;
    }
    return add_poly(A, neg_B);
}

// һԪ����ʽ�˷�
vector<Term> multiply_poly(const vector<Term>& A, const vector<Term>& B) {
    vector<Term> C;
    for (int i = 0; i < A.size(); i++) {
        for (int j = 0; j < B.size(); j++) {
            int degree = A[i].degree + B[j].degree;
            double coeff = A[i].coeff * B[j].coeff;
            bool found = false;
            for (int k = 0; k < C.size(); k++) {
                if (C[k].degree == degree) {
                    C[k].coeff += coeff;
                    found = true;
                    break;
                }
            }
            if (!found && coeff != 0) {
                C.push_back({degree, coeff});
            }
        }
    }
    return C;
}

// �������ʽ
void print_poly(const vector<Term>& P, bool ascending = true, ostream& out = cout) {
    vector<Term> sorted_P = P;
    for (int i = 0; i < sorted_P.size() - 1; i++) {
        int min_idx = i;
        for (int j = i + 1; j < sorted_P.size(); j++) {
            if ((ascending && sorted_P[j].degree < sorted_P[min_idx].degree) ||
                (!ascending && sorted_P[j].degree > sorted_P[min_idx].degree)) {
                min_idx = j;
            }
        }
        if (min_idx != i) {
            swap(sorted_P[i], sorted_P[min_idx]);
        }
    }
    bool first_term = true;
    for (int i = 0; i < sorted_P.size(); i++) {
        if (sorted_P[i].coeff == 0) continue;
        if (!first_term && sorted_P[i].coeff > 0) {
            out << "+";
        }
        if (sorted_P[i].coeff != 1 || sorted_P[i].degree == 0) {
            out << sorted_P[i].coeff;
        }
        if (sorted_P[i].degree > 0) {
            out << "x";
            if (sorted_P[i].degree > 1) {
                out << "^" << sorted_P[i].degree;
            }
        }
        first_term = false;
    }
    out << endl;
}

// �ж϶���ʽ�Ƿ���� 
bool is_dense(const vector<Term>& P, double density_threshold = 0.5) {
    int degree = -1;
    int term_count = 0;
    for (int i = 0; i < P.size(); i++) {
        if (P[i].coeff != 0) {
            if (P[i].degree > degree) {
                degree = P[i].degree;
            }
            term_count++;
        }
    }
    double density = static_cast<double>(term_count) / (degree + 1);
    return density >= density_threshold;
}

int main() {
    vector<Term> P1, P2;
    double coeff;
    int degree = 0;
    cout << "�������һ������ʽ��ϵ����ָ�����Կո�ָ���� 0 0 ��������" << endl;
    while (cin >> coeff >> degree) {
        if (coeff == 0 && degree == 0) break;
        P1.push_back({degree, coeff});
    }
    cout << "������ڶ�������ʽ��ϵ����ָ�����Կո�ָ���� 0 0 ��������" << endl;
    while (cin >> coeff >> degree) {
        if (coeff == 0 && degree == 0) break;
        P2.push_back({degree, coeff});
    }
    
    cout << "��һ������ʽΪ��";
    print_poly(P1);
    cout << (is_dense(P1) ? "�ö���ʽ����" : "�ö���ʽϡ��") << "\n"; 
    cout << "�ڶ�������ʽΪ��";
    print_poly(P2);
    cout << (is_dense(P2) ? "�ö���ʽ����" : "�ö���ʽϡ��") << "\n"; 

    vector<Term> sum = add_poly(P1, P2);
    cout << "��������ʽ�ĺ�Ϊ��\n";
    print_poly(sum);
    print_poly(sum, false);

    vector<Term> diff = subtract_poly(P1, P2);
    cout << "��������ʽ�Ĳ�Ϊ��\n";
    print_poly(diff);
    print_poly(diff, false);

    vector<Term> product = multiply_poly(P1, P2);
    cout << "��������ʽ�Ļ�Ϊ��\n";
    print_poly(product);
    print_poly(product, false);

    ofstream outfile("output.txt");
    if (!outfile) {
        cerr << "�޷����ļ� output.txt" << endl;
        return 1;
    }
    outfile << "��һ������ʽΪ��";
    print_poly(P1, false, outfile);
    
    outfile << "�ڶ�������ʽΪ��";
    print_poly(P2, false, outfile);
    outfile << "��������ʽ�ĺ�Ϊ��";
    print_poly(sum, false, outfile);
    outfile << "��������ʽ�Ĳ�Ϊ��";
    print_poly(diff, false, outfile);
    outfile << "��������ʽ�Ļ�Ϊ��";
    print_poly(product, false, outfile);
    outfile.close();

    return 0;
}
