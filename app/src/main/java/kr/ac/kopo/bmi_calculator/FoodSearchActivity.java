package kr.ac.kopo.bmi_calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.bmi_calculator.databinding.ActivitySubBinding;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodSearchActivity extends AppCompatActivity
{

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request request = chain.request();
                Log.d("요청 URL", request.url().toString());
                return chain.proceed(request);
            })
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.data.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    ApiService apiService = retrofit.create(ApiService.class);
    String apiKey = BuildConfig.API_KEY;

    private ActivitySubBinding binding;

    public FoodSearchActivity() throws UnsupportedEncodingException {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 바인딩 객체 생성
        binding = ActivitySubBinding.inflate(getLayoutInflater());
        // 레이아웃 설정
        setContentView(binding.getRoot());

        String json = loadJSONFromRaw(this);
        List<String> foodList = parseFoodList(json);

        // 2. 어댑터 연결
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                foodList
        );

        // 3. AutoCompleteTextView에 어댑터 설정
        binding.inputMenu.setAdapter(adapter);
        binding.inputMenu.setThreshold(1); // 1자 입력하면 자동완성 시작
        // 자동 완성된 단어 아니면 검색 안되게 만듦
        binding.inputMenu.addTextChangedListener(makeFoodInputWatcher(foodList));

        binding.btnGoToBmi.setOnClickListener(btnGoToBmiListener);
        binding.btnApiCall.setOnClickListener(btnApiCallListener);
        binding.btnReset.setOnClickListener(btnResetListener);

    }

    TextWatcher makeFoodInputWatcher(List<String> foodList) {
        return new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override public void afterTextChanged(Editable s) {



                String input = s.toString().trim();
                if (foodList.contains(input))
                {
                    binding.btnApiCall.setEnabled(true);  // 버튼 활성화
                    // 버튼 활성화 시 배경 색상 변경
                    binding.btnApiCall.setBackgroundTintList(ContextCompat.getColorStateList(FoodSearchActivity.this, R.color.green));
                }
                else
                {
                    binding.btnApiCall.setEnabled(false); // 버튼 비활성화
                    // 버튼 비활성화 시 배경 색상 변경
                    binding.btnApiCall.setBackgroundTintList(ContextCompat.getColorStateList(FoodSearchActivity.this, R.color.resultTextBack));
                }
            }
        };
    }

    View.OnClickListener btnGoToBmiListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(FoodSearchActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 이미 있으면 앞으로
            startActivity(intent);
        }
    };

    View.OnClickListener btnResetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.inputMenu.setText("");
            binding.textKcal.setText("");
            binding.textProtein.setText("");
            binding.textSugar.setText("");
        }
    };

    View.OnClickListener btnApiCallListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {

            if (binding.inputMenu.getText().toString().replace(" ", "").isEmpty())
            {
                Toast.makeText(FoodSearchActivity.this, getString(R.string.menu)+ "을(를) 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.inputMenu.setError(getString(R.string.menu) + " 입력 안됨");
            }


            Call<ApiResponse> call = apiService.getNutritionInfo(
                    apiKey,  // 서비스 키
                    "json",              // 응답 타입
                    binding.inputMenu.getText().toString()       // 음식명
            );
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<ApiResponse.FoodItem> items = response.body().getResponse().getBody().getItems();

                        if (items != null && !items.isEmpty()) {
                            ApiResponse.FoodItem item = items.get(0);  // 첫 번째 항목만 사용

                            // 필요한 데이터만 추출하여 표시
                            binding.textKcal.setText(item.getEnergy() + " kcal"  );
                            binding.textProtein.setText(item.getProtein() + " g");
                            binding.textSugar.setText(item.getSugar() + " g");


                        } else {
                            Toast.makeText(FoodSearchActivity.this, "결과가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FoodSearchActivity.this, "서버 응답 오류: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(FoodSearchActivity.this, "에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("실패", "연결 실패: " + t.getMessage());
                }
            });
        }
    };

//    private void updateUI(Item item) {
//        // 예시로 받은 데이터를 TextView에 표시
//        TextView energyTextView = findViewById(R.id.energyTextView);
//        TextView proteinTextView = findViewById(R.id.proteinTextView);
//        TextView sugarTextView = findViewById(R.id.sugarTextView);
//
//        // 받은 데이터로 텍스트 설정
//        energyTextView.setText("에너지: " + item.getEnerc() + " kcal");
//        proteinTextView.setText("단백질: " + item.getProt() + " g");
//        sugarTextView.setText("당류: " + item.getSugar() + " g");
//    }
//
//    private void showError(String message) {
//        // 에러 메시지 표시
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }



    private String loadJSONFromRaw(Context context) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.food_names);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private List<String> parseFoodList(String jsonString) {
        List<String> foodList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray foodsArray = jsonObject.getJSONArray("foods");
            for (int i = 0; i < foodsArray.length(); i++) {
                foodList.add(foodsArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 바인딩 해제 (메모리 누수 방지)
        binding = null;
    }

}
